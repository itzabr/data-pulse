import os

# Explicitly set JAVA_HOME inside Python to bypass terminal environment caching issues
os.environ["JAVA_HOME"] = r"C:\Program Files\Java\jdk-17"

# Fix for Windows computers with underscores in their name (e.g., Acer_Nitro_V)
os.environ["SPARK_LOCAL_IP"] = "127.0.0.1"

# Fix Hadoop NativeIO missing winutils on Windows
os.environ["HADOOP_HOME"] = r"D:\DataPulse\hadoop"
os.environ["PATH"] = r"D:\DataPulse\hadoop\bin;" + os.environ["PATH"]

# Java 17 compatibility flags universally respected by the JVM
os.environ["JDK_JAVA_OPTIONS"] = (
    "--add-opens=java.base/java.lang=ALL-UNNAMED "
    "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED "
    "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED "
    "--add-opens=java.base/java.io=ALL-UNNAMED "
    "--add-opens=java.base/java.net=ALL-UNNAMED "
    "--add-opens=java.base/java.nio=ALL-UNNAMED "
    "--add-opens=java.base/java.util=ALL-UNNAMED "
    "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED "
    "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED "
    "--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED "
    "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED "
    "--add-opens=java.base/sun.nio.cs=ALL-UNNAMED "
    "--add-opens=java.base/sun.security.action=ALL-UNNAMED "
    "--add-opens=java.base/sun.util.calendar=ALL-UNNAMED "
    "--add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED"
)

from pyspark.sql import SparkSession
from pyspark.sql.functions import count, sum
import glob

spark = (
    SparkSession.builder
    .appName("DataPulse")
    .master("local[*]")
    .getOrCreate()
)

# Bypass the infamous Windows Hadoop 'NativeIO' wildcard bug
file_paths = [os.path.abspath(f) for f in glob.glob("data/*.json")]
df = spark.read.json(file_paths)

print("===== RAW DATA =====")
df.show()

print("===== EVENT COUNTS =====")

event_counts = (
    df.groupBy("eventType")
      .agg(
          count("*").alias("total_events")
      )
)

event_counts.show()

print("===== REVENUE BY SELLER =====")

revenue_df = (
    df.filter(df.eventType == "ORDER_PLACED")
      .groupBy("sellerId")
      .agg(
          sum("totalAmount").alias("total_revenue")
      )
)

revenue_df.show()

print("===== WRITING PARTITIONED PARQUET =====")

df.write.mode("overwrite") \
    .partitionBy("eventType") \
    .parquet("output/events_parquet")

print("===== READING PARQUET =====")

parquet_df = spark.read.parquet("output/events_parquet")
parquet_df.show()

spark.stop()

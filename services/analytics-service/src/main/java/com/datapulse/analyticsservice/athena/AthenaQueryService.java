package com.datapulse.analyticsservice.athena;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.*;
import com.datapulse.analyticsservice.dto.EventCountResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AthenaQueryService {

    private final AthenaClient athenaClient;

    public String executeQuery() {

        StartQueryExecutionRequest request =
                StartQueryExecutionRequest.builder()
                        .queryString("""
                                SELECT
                                    eventType,
                                    COUNT(*)
                                FROM datapulse_events_parquet
                                GROUP BY eventType
                                """)
                        .queryExecutionContext(
                                QueryExecutionContext.builder()
                                        .database("datapulse")
                                        .build()
                        )
                        .resultConfiguration(
                                ResultConfiguration.builder()
                                        .outputLocation(
                                                "s3://datapulse-athena-results/"
                                        )
                                        .build()
                        )
                        .build();

        StartQueryExecutionResponse response =
                athenaClient.startQueryExecution(
                        request
                );

        return response.queryExecutionId();

    }

    public List<EventCountResponse> getEventCounts()
            throws InterruptedException {

        StartQueryExecutionRequest request =
                StartQueryExecutionRequest.builder()
                        .queryString("""
                                SELECT
                                    eventType,
                                    COUNT(*)
                                FROM datapulse_events_parquet
                                GROUP BY eventType
                                """)
                        .queryExecutionContext(
                                QueryExecutionContext.builder()
                                        .database("datapulse")
                                        .build()
                        )
                        .resultConfiguration(
                                ResultConfiguration.builder()
                                        .outputLocation(
                                                "s3://datapulse-athena-results/"
                                        )
                                        .build()
                        )
                        .build();

        StartQueryExecutionResponse response =
                athenaClient.startQueryExecution(
                        request
                );

        String queryExecutionId =
                response.queryExecutionId();

        Thread.sleep(5000);

        GetQueryResultsResponse results =
                athenaClient.getQueryResults(
                        GetQueryResultsRequest.builder()
                                .queryExecutionId(
                                        queryExecutionId
                                )
                                .build()
                );

        List<EventCountResponse> answer =
                new ArrayList<>();

        List<Row> rows =
                results.resultSet().rows();

        for (int i = 1; i < rows.size(); i++) {

            Row row = rows.get(i);

            answer.add(

                    new EventCountResponse(

                            row.data()
                                    .get(0)
                                    .varCharValue(),

                            Long.parseLong(

                                    row.data()
                                            .get(1)
                                            .varCharValue()

                            )

                    )

            );

        }

        return answer;

    }

}

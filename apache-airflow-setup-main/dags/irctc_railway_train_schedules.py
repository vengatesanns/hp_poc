
from airflow.models import DAG
from airflow.providers.apache.spark.operators.spark_submit import SparkSubmitOperator
from airflow.providers.postgres.operators.postgres import PostgresOperator


from datetime import datetime

default_args = {
    'start_date': datetime(2022, 1, 1)
}

JAR_VERSION = "2.2.0"

with DAG('irctc_railway_train_schedules', schedule_interval='@daily', 
        default_args=default_args, catchup=False) as dag:
    # Task 1 - Flattening the JSON to ORC file
    flattening_data_spark_submit_task = SparkSubmitOperator(
        task_id = 'flattening_data_spark_submit_task',
        conn_id='spark_submit_connection',
        java_class= 'com.hackprotech.railways.RailwaysDataFlatteningUsingDF',
        application=f"/home/vengat/big_data/projects/spark_jars/spark-realtime-projects-assembly-{JAR_VERSION}.jar",
        
    )

    #  Task 2 - Derive the Train Schedule details
    train_schedule_details = SparkSubmitOperator(
        task_id="train_schedules_spark_submit_task",
        conn_id='spark_submit_connection',
        java_class= 'com.hackprotech.railways.RailwayScheduleDetailsDF',
        application=f"/home/vengat/big_data/projects/spark_jars/spark-realtime-projects-assembly-{JAR_VERSION}.jar",
    )

    # Task 3 - Create train_schedules table in postgresql
    create_tables = PostgresOperator


    # Task 4 - Load CSV file into table in postgresql

    flattening_data_spark_submit_task >> train_schedule_details
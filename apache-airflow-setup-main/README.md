# apache-airflow-setup


# Requirements
      

 - Linux Machine (VM or any other mac os)
 - Python
 

# Steps to install airflow in linux

 1. Install **VirtualBox** or **Vmware** for ***linux machine***
 2. Install Python using **dead-snakes ppa repos** or **anaconda**
 3. Check python version using - **python --version**
 4. Create the virtual env using - **python -m venv env-name**
 5. To activate the new env using - **source env-name/bin/activate**
 6. Once activated, try to install the apache airflow using - **pip install apache-airflow**

# How to run airflow?

 1. To check the airflow versions - **airflow info** 
 2. To initialize the **METASTORE** in airflow - **airflow db init**
 3. To start the **WEBSERVER** in airflow - **airflow webserver --port 8080**


# Airflow Commands

 1. Create new user - ***airflow users create -u <"name"> -p <"password"> -f <"first-name"> -l <"last-name"> -r <"roles"> -e <"email">***
 2. To initialize the **METASTORE** in airflow - ***airflow db init***
 3. To upgrade DB to new version - ***airflow db upgrade***
 4. To reset DB  - ***airflow db reset***
 5. To start the **WEBSERVER** in airflow - ***airflow webserver --port 8080*** 
 6. To start the **SCHEDULER** in airflow - ***airflow scheduler***
 7. To get all **DAG** list - ***airflow dags list***
 8. To get all the **TASK** under the **DAG** - ***airflow tasks list <"dag-id">***
 9. To trigger the **DAG in particular date** - ***airflow dags trigger -e <"date">  <"dag-id">*** 




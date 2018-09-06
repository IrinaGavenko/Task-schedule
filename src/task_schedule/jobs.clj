(ns task-schedule.jobs
  (:require [clj-time.local :as l]))

(def task-list [{:task-type "task1"
                 :schedule 5
                 :run-at (l/local-now)
                 :params [1]}
                {:task-type "task2"
                 :schedule 10
                 :run-at (l/local-now)
                 :params [2 3]}
                {:task-type "task3"
                 :schedule 12
                 :run-at (l/local-now)
                 :params [4 5 6]}
                {:task-type "task4"
                 :schedule 15
                 :run-at (l/local-now)
                 :params [7 8 9 10]}])
(ns task-schedule.core
  (:require [clojure.string :as cstr]))

;; Add parameters of new task

(def task-list [{:task-type "task1"
                 :schedule 5
                 :params [1]}
                {:task-type "task2"
                 :schedule 10
                 :params [2 3]}
                {:task-type "task3"
                 :schedule 12
                 :params [4 5 6]}
                {:task-type "task4"
                 :schedule 15
                 :params [7 8 9 10]}])

;; Add execution function of new task

(defn task1-processing
  "Processing of task1"
  [values]
  (println (str "task-1: " (+ 1 (reduce + values)))))

(defn task2-processing
  "Processing of task2"
  [values]
  (println (str "task-2: " (+ 2 (reduce + values)))))

(defn task3-processing
  "Processing of task3"
  [values]
  (println (str "task-3: " (+ 3 (reduce + values)))))

(defn task4-processing
  "Processing of task4"
  [values]
  (println (str "task-4: " (+ 4 (reduce + values)))))

(def test-task {:task-type "task3"
           :schedule 5000
           :params [1 2 3]})


#_(new-task task)

#_(defn run
  "Main function -- run application"
  [task-list]
  (run! new-task task-list))

#_(def curr-time 15)


;; Есть ошибка в update!!!
(defn launch-task
  "Find and launch processing of new task"
  [{:keys [task-type schedule params] :as task}]
  (case task-type
    "task1" (task1-processing params)
    "task2" (task2-processing params)
    "task3" (task3-processing params)
    "task4" (task4-processing params))
  (update task :schedule inc)
  (println (task :schedule)))

(launch-task test-task)


;; Костыльный способ конвертации в нужный формат
(defn convert-week
  [week]
  (case week
    "Mon" 1
    "Tue" 2
    "Wen" 3
    "Thu" 4
    "Fri" 5
    "Sut" 6
    "Sun" 7))

(defn convert-month
  [month]
  (case month
    "Jan" 1
    "Feb" 2
    "Mar" 3
    "Apr" 4
    "May" 5
    "Jun" 6
    "Jul" 7
    "Aug" 8
    "Sep" 9
    "Oct" 10
    "Nov" 11
    "Dec" 12))

(defn curr-time
  []
  (let [[week month date time city year]
        (cstr/split (.toString (java.util.Date.)) #" ")]
    (let [[hours mins secs] (cstr/split time #":")]
      {:m   (Integer/parseInt mins)
       :h   (Integer/parseInt hours)
       :dom (convert-week week)
       :mon (convert-month month)
       :dow (Integer/parseInt year)})))


(curr-time)

#_(def curr-time 13)

;; schedule format:
;; m h dom mon dow

(defn check-time
  "Necessity to run a task"
  [schedule]
  (<= schedule curr-time))

(defn next-task
  [task]
  (if (check-time (task :schedule))
    (launch-task task)))

;; run application

#_(defn run
  [task-list]
  (run! next-task task-list)
  (Thread/sleep 5000)
  (recur task-list))

#_(run task-list)
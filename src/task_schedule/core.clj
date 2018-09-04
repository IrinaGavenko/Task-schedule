(ns task-schedule.core)

(def task-list [{:task-type "hello"
                 :schedule 5000
                 :params [1]}
                {:task-type "hello2"
                 :schedule 10000
                 :params [2 3]}
                {:task-type "hello3"
                 :schedule 12000
                 :params [4 5 6]}])

(defn hello-processing
  [values]
  (println values))

(defn hello2-processing
  [values]
  (println values))

(defn hello3-processing
  [values]
  (println values))

#_(def task {:task-type "hello2"
           :schedule 5000
           :params [1 2]})

(def stop-value 1)

(defn next-task
  [task]
  (println (task :task-type))
  (case (task :task-type)
    "hello" (hello-processing (task :params))
    "hello2" (hello-processing (task :params))
    "hello3" (hello-processing (task :params)))
  ((Thread/sleep (task :schedule))))

#_(next-task task)

(defn run-task
  [task-list]
  (while (< stop-value 2) (do
                           (run! (fn [task'] (next-task task')) task-list)
                           (println stop-value)
                           (def stop-value (inc stop-value)))))

(run-task task-list)
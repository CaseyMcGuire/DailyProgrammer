(use '[clojure.string :only (join split)])

(defn scramble-str
  "Randomly shuffles the letters in a string except the first and last characters."
  [word]
  (let [str-length (count word)]
    (if (<= str-length 2)
      word
      (let [str-end (- str-length 1)
            first-char (subs word 0 1)
            last-char (subs word (- str-length 1))
            shuffled-inner-str (join (shuffle (seq (subs word 1 str-end))))]
        (str first-char shuffled-inner-str last-char)))))

(doseq [line (line-seq (java.io.BufferedReader. *in*))]
  (let [cur-line (join " " (map #(scramble-str %) (split line #" ")))]
    (println cur-line)))

        
            

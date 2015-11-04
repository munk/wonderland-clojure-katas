(ns alphabet-cipher.coder)

(def alphabet (cycle "abcdefghijklmnopqrstuvwxyz"))

(defn cipher-row
  [n]
  (take 26
        (drop n alphabet)))

(defn lookup [s b]
  (let [idx-s (.indexOf (cipher-row 0) s)
        idx-b (.indexOf (cipher-row 0) b)
        row (cipher-row idx-b)]
    (nth row idx-s)))

(defn search [s b]
  (let [alpha (cipher-row 0)
        idx-s (.indexOf alpha s)
        idx-b (.indexOf alpha b)
        row (cipher-row idx-s)
        chr (nth alpha idx-b)]
    (nth alphabet
         (first (filter #(= chr (nth row %))
                        (range 26))))))

(defn encode [keyword message]
  (let [ct (count message)
        sk (take ct (cycle keyword))]
     (apply str (map lookup sk message))))

(defn decode [keyword message]
  (let [ct (count message)
        sk (take ct (cycle keyword))]
     (apply str (map search sk message))))

(defn decypher [cypher message]
  (let [sk' (decode message cypher)]
    (loop [ct 1]
      (let [sk (apply str (take ct sk'))]
        (if (= message (decode sk cypher))
          sk
          (recur (inc ct)))))))

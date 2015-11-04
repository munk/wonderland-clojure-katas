(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn rank-val [card]
  (let [[_ rank] card]
    (.indexOf ranks rank)))

(defn suit-val [card]
  (let [[suit _] card]
    (.indexOf suits suit)))

(defn card-val [card]
  [(rank-val card) (suit-val card)])

(defn play-round [player1-card player2-card]
  (println "Player One: " player1-card)
  (println "Player Two: " player2-card)
  (let [[p1-suit p1-rank] (card-val player1-card)
        [p2-suit p2-rank] (card-val player2-card)
        prize (shuffle [player1-card player2-card])]
    (cond (> p1-rank p2-rank) [prize []]
          (> p2-rank p1-rank) [[] prize]
          (> p1-suit p2-suit) [prize []]
          :else [[] prize])))

(defn merge-cards [hand prize]
  (cond (empty? prize) hand
        :else (into hand prize)))

(defn play-game [player1-cards player2-cards]
  (cond (empty? player1-cards) :player-2
        (empty? player2-cards) :player-1
        :else (let [p1-card (first player1-cards)
                    p2-card (first player2-cards)
                    p1-hand (into [] (rest player1-cards))
                    p2-hand (into [] (rest player2-cards))
                    [p1-prize p2-prize] (play-round p1-card p2-card)]
                (println "Result: " p1-prize p2-prize)
                (recur (merge-cards p1-hand p1-prize)
                       (merge-cards p2-hand p2-prize)))))

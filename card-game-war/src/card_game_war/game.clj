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
          true [[] prize])))

(defn play-game [player1-cards player2-cards]
  (cond (empty? player1-cards) :player-2
        (empty? player2-cards) :player-1
        true (let [c1 (first player1-cards)
                   c2 (first player2-cards)
                   c1' (rest player1-cards)
                   c2' (rest player2-cards)
                   [p1 p2] (play-round c1 c2)]
               (println player1-cards player2-cards)
               (recur (into c1' [p1]) (into c2' [c2])))))

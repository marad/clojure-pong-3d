(ns jme-clojure.cartoon
  (:require [jme-clojure.assets :as assets])
  (:import [com.jme3.scene Geometry Node]
           [com.jme3.math ColorRGBA]
           [com.jme3.post FilterPostProcessor]
           [com.jme3.post.filters CartoonEdgeFilter]))


(defn- create-toon-filter [num-samples]
  (let [fpp (FilterPostProcessor. assets/manager)
        toon-filter (CartoonEdgeFilter.)]
    (when (pos? num-samples) (.setNumSamples fpp num-samples))
    (.setEdgeColor toon-filter ColorRGBA/Gray)
    (.addFilter fpp toon-filter)
    fpp))

(defn install-toon-filter [app]
  (.addProcessor (.getViewPort app)
                 (create-toon-filter (-> app
                                         .getContext
                                         .getSettings
                                         .getSamples))))

(defn make-toonish [spatial]
  (condp instance? spatial
    Node (dorun (map make-toonish (.getChildren spatial)))
    Geometry (let [m (.getMaterial spatial)
                   t (assets/load-texture "Textures/ColorRamp/toon.png")]
               (when (-> m .getMaterialDef (.getMaterialParam "UseMaterialColors"))
                 (doto m
                   (.setTexture "ColorRamp" t)
                   (.setBoolean "UseMaterialColors" true)
                   (.setColor "Specular" ColorRGBA/Black)
                   ;;(.setColor "Diffuse" ColorRGBA/White)
                   (.setBoolean "VertexLighting" true)
                   ))
               )))


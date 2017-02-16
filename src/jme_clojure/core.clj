(ns jme-clojure.core
  (:require [jme-clojure.cartoon :refer [make-toonish install-toon-filter]]
            [jme-clojure.assets :as assets])
  (:import [com.jme3.app SimpleApplication]
           [com.jme3.system AppSettings]
           [com.jme3.material Material]
           [com.jme3.math Vector3f Quaternion FastMath]
           [com.jme3.scene Geometry Node CameraNode]
           [com.jme3.scene.shape Box Sphere]
           [com.jme3.math ColorRGBA ]
           [com.jme3.light DirectionalLight])
  (:gen-class))

(def ^:dynamic *app-settings* (doto (AppSettings. true)
                                (.setFullscreen false)
                                (.setTitle "My Fun Project")))

(defn- setup-lights [root-node]
  (.addLight root-node (doto (DirectionalLight.)
                         (.setDirection (doto (Vector3f. -1 -1 1) (.normalizeLocal)))
                         (.setColor (ColorRGBA. 2 2 2 1)))))

(defn- set-color [spatial color]
  (condp instance? spatial
    Node (dorun (map #(set-color % color) (.getChildren spatial)))
    Geometry (doto (.getMaterial spatial)
               (.setColor "Diffuse" color))))

(defn load-paddle [root-node]
  (let [blue-paddle (doto (-> (assets/load-model "Models/paddle.blend") (.getChild "Paddle"))
                      (set-color ColorRGBA/Blue)
                      (make-toonish)
                      (.setName "Blue Paddle")
                      (.setLocalRotation (.fromAngles (Quaternion/IDENTITY) 0 FastMath/HALF_PI 0))
                      (.setLocalTranslation 2 0 0))
        red-paddle (doto (.deepClone blue-paddle)
                     (set-color ColorRGBA/Red)
                     (.setLocalTranslation -2 0 0)
                     (.setName "Red Paddle"))]
    (.attachChild root-node blue-paddle)
    (.attachChild root-node red-paddle)
    ))

(defn- create-a-box [root-node]
  (let [b (Sphere. 100 100 1.0)
        ;;b (Box. 1 1 1)
        mat (doto (Material. assets/manager "Common/MatDefs/Light/Lighting.j3md")
              (.setColor "Diffuse" ColorRGBA/White))
        geom (doto (Geometry. "Ball" b)
               (.setMaterial mat))]
    (make-toonish geom)
    (.attachChild root-node geom)))

(defn- setup-scene [root-node]
  (doto root-node
    (setup-lights)
    (load-paddle)
    (create-a-box)
    ))

(defn- configure-camera [app]
  (let [flyCam (.getFlyByCamera app)
        node (CameraNode. "Camera" (.getCamera app))]
    (.setEnabled flyCam false)
    (.attachChild (.getRootNode app) node)
    (.setLocalTranslation node (Vector3f. 0 20 20))
    (.lookAt node Vector3f/ZERO Vector3f/UNIT_Y)
    ))

(defn- create-app []
  (proxy [SimpleApplication] []
    (simpleInitApp []
      (install-toon-filter this)
      (configure-camera this)
      (setup-scene (.getRootNode this)))))

(defn- start-game []
  (doto (create-app)
    (.setShowSettings false)
    (.setSettings *app-settings*)
    (.start)))

(defn -main [& args]
  (start-game))

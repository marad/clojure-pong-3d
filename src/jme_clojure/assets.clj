(ns jme-clojure.assets
  (:import [com.jme3.system JmeSystem]
           [com.jme3.scene.plugins.blender BlenderModelLoader]))

(def desktop-cfg (.getResource
                  (.getContextClassLoader (Thread/currentThread))
                  "com/jme3/asset/Desktop.cfg"))

(def manager
  (doto (JmeSystem/newAssetManager desktop-cfg)
    (.registerLoader BlenderModelLoader (into-array ["blend"]))))

(defn load-texture [path] (.loadTexture manager path))

(defn load-model [path] (.loadModel manager path))

(defn load-material [path] (.loadMaterial manager path))

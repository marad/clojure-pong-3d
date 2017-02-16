(defproject jme_clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories [["bintray" "http://dl.bintray.com/jmonkeyengine/org.jmonkeyengine"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.jmonkeyengine/jme3-core "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-effects "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-networking "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-plugins "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-terrain "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-blender "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-bullet "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-bullet-native "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-niftygui "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-desktop "3.1.0-beta4"]
                 [org.jmonkeyengine/jme3-lwjgl3 "3.1.0-beta4"]
                 ]
  :main ^:skip-aot jme-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

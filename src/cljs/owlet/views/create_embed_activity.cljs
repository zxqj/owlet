(ns owlet.views.create-general-activity
  (:require [owlet.components.creation.create-activity-title :refer [create-activity-title]]
            [owlet.components.creation.create-activity-response :refer [create-activity-response-component]]
            [owlet.components.creation.select-branches :refer [select-branches]]
            [owlet.components.creation.select-tags :refer [select-tags]]
            [owlet.components.creation.select-platform :refer [select-platform]]
            [owlet.components.creation.general-activity-text-fields :refer [general-activity-text-fields]]
            [owlet.components.creation.create-preview-image :refer [create-preview-image]]
            [owlet.views.login-only :refer [login-only-view]]
            [reagent.core :as reagent]
            [re-frame.core :as rf]))

(def embed-code (reagent/atom ""))
(def show-embed? (reagent/atom false))
(def show-upload? (reagent/atom false))

(defn create-general-activity []
   [:div.activity
    [:div.activity-wrap
     [:div.col-sm-12
      [:h1 "creating: an embed-based activity guide"]
      [:div.activity-header.col-sm-12.col-lg-7
       [create-activity-title]]]
     [:div.activity-content.col-sm-12.col-lg-5
      [:h1 "metadata"]
      [:div.activity-creation-wrap
       [:div#select-categories
        [:div [:h5 [:mark "Branch(es)"]]
         [select-branches false]]
        [:div [:h5 [:mark "Platform"]]
         [select-platform false]]
        [:div [:h5 [:mark "Tag(s)"]]
         [select-tags true]]]
       [general-activity-text-fields]]]
     [:div.activity-content.col-sm-12.col-lg-7
      [:h1 "activity content"]
      [:div.activity-creation-wrap
       [:h5 [:mark "Embed Code"]]
       [:h6 "Suggestions:"
        [:ul
         [:li "Embed a slideshow from "
          [:a {:href "https://slides.com"
               :target "_blank"}
            "slides.com"]]
         [:li "Embed a video from "
          [:a {:href "https://vimeo.com"
               :target "_blank"}
            "vimeo.com"]]
         [:li "Write your own HTML"]]]
       [:textarea {:rows "2"
                   :placeholder "Paste your code here."
                   :value @embed-code
                   :on-change (fn [v]
                                (reset! embed-code (.. v -target -value))
                                (reset! show-embed? true))}]
       (when @show-embed?
         [:div.embed-container {"dangerouslySetInnerHTML"
                                #js{:__html @embed-code}}])]
      [:div.activity-creation-wrap
       [create-preview-image]]
      [:div.create-activity-buttons
       [:button.save-activity
        "Save Activity"]]]
     [create-activity-response-component :ok]]])

(defn create-general-activity-view []
  (if @(rf/subscribe [:my-id])
    [create-general-activity]
    [login-only-view]))

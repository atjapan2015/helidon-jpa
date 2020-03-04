/**
 * @license
 * Copyright (c) 2014, 2019, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 * @ignore
 */
/*
 * Your incidents ViewModel code goes here
 */
define(['accUtils', 'knockout', 'ojs/ojbootstrap', 'ojs/ojmodel', 'ojs/ojcollectiondataprovider', 'ojs/ojarraydataprovider', 'ojs/ojknockout', 'ojs/ojtable'],
    function (accUtils, ko, Bootstrap, Model, CollectionDataProvider, ArrayDataProvider) {

        function IncidentsViewModel() {
            let self = this;
            // Below are a set of the ViewModel methods invoked by the oj-module component.
            // Please reference the oj-module jsDoc for additional information.

            /**
             * Optional ViewModel method invoked after the View is inserted into the
             * document DOM.  The application can put logic that requires the DOM being
             * attached here.
             * This method might be called multiple times - after the View is created
             * and inserted into the DOM and after the View is reconnected
             * after being disconnected.
             */
            self.connected = function () {
                let _self = this;
                _self.dataprovider = ko.observable();
                // _self.itemsArray = ko.observable('');
                accUtils.announce('Items page loaded.', 'assertive');
                document.title = "Items";
                // Implement further logic if needed

                const itemsUrl = "/workshop/items";

                // $.getJSON(itemsUrl).then(function (data) {
                //     let itemsArray = data;
                //     console.log("items", itemsArray);
                //     });
                // });

                //Single line of data
                let itemModel = Model.Model.extend({
                    urlRoot: itemsUrl,
                    idAttribute: 'itemId'
                });

                //Multiple models i.e. multiple lines of data
                let itemCollection = new Model.Collection.extend({
                    url: itemsUrl,
                    model: itemModel,
                    comparator: 'itemId'
                });

                _self.myItemCollection = new itemCollection();
                _self.dataprovider(new CollectionDataProvider(_self.myItemCollection));
            };

            /**
             * Optional ViewModel method invoked after the View is disconnected from the DOM.
             */
            self.disconnected = function () {
                // Implement if needed
            };

            /**
             * Optional ViewModel method invoked after transition to the new View is complete.
             * That includes any possible animation between the old and the new View.
             */
            self.transitionCompleted = function () {
                // Implement if needed
            };
        }

        /*
         * Returns an instance of the ViewModel providing one instance of the ViewModel. If needed,
         * return a constructor for the ViewModel so that the ViewModel is constructed
         * each time the view is displayed.
         */
        return IncidentsViewModel;
    }
);

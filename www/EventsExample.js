var cordova = require('cordova'),
    exec = require('cordova/exec');

// Create the event.
var event = document.createEvent('Event');
// Define that the event name is 'bar-event'.
event.initEvent('bar-event', true, true);

require('cordova/channel').onCordovaReady.subscribe(function() {
    require('cordova/exec')(win, null, 'EventsExample', 'method', []);
    function win(message) {        
        console.log("Got event: " + JSON.stringify(message));
        
        // target can be any Element or other EventTarget.
        document.dispatchEvent(event);        
        console.log("Send new event 'bar-event'");
    }
});

var EventsExample = function() {
        this.options = {};
};

EventsExample.prototype = {
    createEvent: function(actionName, params, successCallback, errorCallback) {
      cordova.exec(
          successCallback, // success callback function
          errorCallback, // error callback function
          'EventsExample', // mapped to our native Java class, by entry in config.xml <feature name="EventsExample">
          actionName, // with this action name
          [params]
      );
    },

     status: function (successCallback, errorCallback) {
       this.createEvent('status', null, successCallback, errorCallback);
     }
};

var EventsExampleInstance = new EventsExample();

module.exports = EventsExampleInstance;


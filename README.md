This about how to send events from your native android code to the JavaScript part. This can be used to notify your JavaScript app without to register any callback in JavaScript first, i.e. hardware button or sensor trigger or any other occurence of device actions.

# How to start 
## Using AngularJS

this is how you can initiate an event which is created in the native android code and send later to your AngularJS controller:

    $scope.sendStatus = function() {
        window.cordova.EventsExample.status(function(data){
            console.log("Result: " + angular.toJson(data));
        }, function(error){
            console.error("Error: " + angular.toJson(error));
        });
    }

you can define a button:

    <button ng-click="sendStatus()">Send Status</button>

to trigger the event manually

and then listen to the event in your controller with:

    // Listen for the event.
    document.addEventListener('bar-event', function (e) {
        $timeout(function(){
            console.log("Received Event: " + angular.toJson(e))
            console.log("Received Event-Type: " + angular.toJson(e.type))
        });
    }, false);
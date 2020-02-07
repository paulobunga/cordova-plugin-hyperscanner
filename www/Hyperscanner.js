var exec = require('cordova/exec');

module.exports = {

    registerKeyDown: function (success, error) {
        exec(success, error, 'Hyperscanner', 'registerKeyDown');
    },

    registerKeyUp: function (success, error) {
        exec(success, error, 'Hyperscanner', 'registerKeyUp');
    },

    startRFIDScanner: function (success, error) {
        exec(success, error, 'Hyperscanner', 'startRFIDScanner');
    },

    stopRFIDScanner: function (success, error) {
        exec(success, error, 'Hyperscanner', 'stopRFIDScanner');
    },

    startBarcodeScanner: function (success, error) {
        exec(success, error, 'Hyperscanner', 'startBarcodeScanner');
    },

    stopBarcodeScanner: function (success, error) {
        exec(success, error, 'Hyperscanner', 'stopBarcodeScanner');
    }
}

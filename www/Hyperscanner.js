var exec = require('cordova/exec');

module.exports = {

    registerKeyDown: function (success, error) {
        exec(success, error, 'Hyperscanner', 'registerKeyDown');
    },

    registerKeyUp: function (success, error) {
        exec(success, error, 'Hyperscanner', 'registerKeyUp');
    },
}

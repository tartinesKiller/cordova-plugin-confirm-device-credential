/*global cordova, module*/

module.exports = {
    confirm: function () {
        return new Promise((resolve, reject) => {
            cordova.exec((succ) => {
                resolve(succ)
            }, (err) => {
                reject(err);
            }, "ConfirmDeviceCredential", "confirm");
        });
    }
};

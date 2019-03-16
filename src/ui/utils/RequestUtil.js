
export function sendGet(url, callback, errorCallback) {
    fetch(url, {
        method: 'GET',
    }).then((response) => {
        if (response.status == 200) {
            return response.json();
        } else {
            errorCallback();
        }
    }).then((value) => {
        callback(value);
    });
};

export function sendJson(url, body, callback, errorCallback) {
    fetch(url, {
        method: 'POST',
        body: JSON.stringify(body),
        headers: { 'Content-Type': 'application/json' }
    }).then((response) => {
        if (response.status == 200) {
            return response.json();
        } else {
            errorCallback(response.status);
        }
    }).then((value) => {
        callback(value);
    });
}

export function sendDelete(url, callback, errorCallback) {
    fetch(url,
        {
            method: 'DELETE'
        }).then((response) => {
            if (response.status == 200) {
                callback();
            } else {
                errorCallback(response.status);
            }
        });
}

export function logout(callback) {
    fetch('/perform_logout', {
        method: 'POST',
    }).then(() => {
        callback();
    });
}

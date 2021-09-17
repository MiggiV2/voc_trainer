export function getAuthURL() {
    return "https://discord.com/api/oauth2/authorize" +
        "?client_id=888091850467065936" +
        "&response_type=token" +
        "&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2Fcallback" +
        "&scope=identify";
}

export function getURLHashParams() {
    var hash = window.location.hash.substr(1);
    var result = hash.split('&').reduce(function (res, item) {
        var parts = item.split('=');
        res[parts[0]] = parts[1];
        return res;
    }, {});
    return result;
}
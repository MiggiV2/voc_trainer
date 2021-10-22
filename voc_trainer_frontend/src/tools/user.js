import { setCookie } from "../tools/cookie";
import { HOST } from "./auth";

export function loadUser(accessToken, funcToRun) {
    fetch(HOST + 'api/get/account-info', {
        headers: {
            'Authorization': 'Bearer ' + accessToken,
        },
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            }
            else if (response.status === 401) {
                logout();
            }
        })
        .then(response => {
            if (response != null) {
                var role = response.admin ? '1' : '0';
                localStorage.setItem('avatar', response.avatar);
                localStorage.setItem('id', response.id);
                localStorage.setItem('username', response.name);
                localStorage.setItem('role', role);
                funcToRun();
            }
        });
}

export function getAvatarURL() {
    const avatar = localStorage.getItem('avatar');
    const id = localStorage.getItem('id');
    return (avatar == null || avatar.length < 10)
        ? null
        : 'https://cdn.discordapp.com/avatars/' + id + '/' + avatar + '.webp?size=128'
}

export function getAvatarURLFromUser(user) {
    const avatar = user.avatar;
    const id = user.id;
    return (avatar == null || avatar.length < 10)
        ? null
        : 'https://cdn.discordapp.com/avatars/' + id + '/' + avatar + '.webp?size=128'
}

function logout() {
    localStorage.removeItem("username");
    localStorage.removeItem("avatar");
    localStorage.removeItem("id");
    setCookie("access_token", "", -1);
    setCookie("season_id", "", -1);
    window.location = "/";
}
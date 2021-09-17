export function loadUser(accessToken, funcToRun) {
    fetch('https://discord.com/api/oauth2/@me', {
        headers: {
            'Authorization': 'Bearer ' + accessToken,
        },
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
        })
        .then(response => {
            if (response != null) {
                localStorage.setItem('avatar', response.user.avatar);
                localStorage.setItem('id', response.user.id);
                localStorage.setItem('username', response.user.username);
                funcToRun();
            }
        });
}

export function getAvatarURL() {
    const avatar = localStorage.getItem('avatar');
    const id = localStorage.getItem('id');
    return 'https://cdn.discordapp.com/avatars/' + id + '/' + avatar + '.webp?size=128'
}
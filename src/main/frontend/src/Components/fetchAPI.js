function fetchAPI(path) {
    fetch(path)
        .then((response) => {
            return response;
        })
        .then((myJson) => {
            console.log(myJson);
        });
}

export default fetchAPI;
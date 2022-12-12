const search =()=>{
    console.log("searching....")
    // let id = document.getElementByID('searchid');
    let search_box = $("#searchid").val();

    if (search_box=="") {
        $(".search-result").hide();

    }else{
        console.log(search_box);

        let url =`http://localhost:8080/search/${search_box}`;
        fetch(url)
        .then((response)=>{
            return response.json();
        })
        .then((data)=>{
            console.log(data);
            let element = `<div class='list-group'>`;

            data.forEach(contact => {
                element+=`<a href='/user/user_contact/${contact.contID}' class='list-group-item list-group-action'>${contact.contName}</a>`
            });
            element+=`</div>`;
            $(".search-result").html(element);
        });

        $(".search-result").show();
    }
}
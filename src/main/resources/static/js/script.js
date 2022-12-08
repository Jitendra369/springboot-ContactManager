
// alert("hell");
const naviBar= () =>{
    alert("sidetogglerbar() function");
    if ($(".siderbar").is(":visible")) {
        console.log($(".siderbar").is(":visible"));
        
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");
    }else{
        console.log($(".siderbar").is(":visible"));
        
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%");
    }
}

// const toggle=() =>{
//     let sidebar = document.getElementById("sidebar");
//     let content = document.getElementById("content");
//     if (sidebar.style.display ==="none") {
//         sidebar.style.display ="block";
//         content.style.marginLeft = "0%";
        
//     }else{
//         sidebar.style.display ="none";
//         content.style.marginLeft = "20%";
//     }
// }
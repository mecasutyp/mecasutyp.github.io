function setCookie(cname,cvalue,exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname+"="+cvalue+"; "+expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function checkCookie() {
    var user=getCookie("NotifiT");
    if (user != "") {
    //        alert("Welcome again " + user);
        $( "#dialog-message" ).hide();
    } else {
        $( "#dialog-message" ).dialog({
            modal: true,           
            width: 500,
            buttons: {
                "Acepto T\u00E9rminos y Condiciones": function() {
                    if($("#radNot2").attr('checked')){
                        setCookie("NotifiT", "ok", 30);
                        //document.getElementById('oculto').style.display='none';
                    }
                    $( this ).dialog( "close" );
                    
                }
            }
        });
    }
}


    


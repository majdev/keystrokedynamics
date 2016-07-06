$(document).ready(main); 

function check(form)/*function to check userid & password*/
{
/*the following code checkes whether the entered userid and password are matching*/
	if(form.username.value == "k" && form.password.value == "n")
	{
		// console.log(form.username.value);
		 setCookie("loginName", form.username.value,7);
		// console.log("cookie set");
	 //  	window.location = "http://www.google.com";/*opens the target page while Id & password matches*/
	 window.location = "../../staticTexts.html";	  	
	}
	else
	{
	 	alert("Error Password or Username")/*displays error message*/
	}
}

 function main(){

//disable atocomplete
 if( navigator.userAgent.toLowerCase().indexOf('chrome') >= 0 ) {
        setTimeout(function () {
            document.getElementById('username').autocomplete = 'off';
            
	    //TODO disable for all

        }, 1);
    }
}




function setCookie(name,value,days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        var expires = "; expires="+date.toGMTString();
    }
    else var expires = "";
    document.cookie = name+"="+value+expires;
    console.log(document.cookie);
}
function getCookie(name) 
{
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}
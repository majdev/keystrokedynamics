var keyLogs = [];       //DS to store keystroke
var resetsInAWord = [];
var wrongAuthenticationRetries = 0;
var reloadIndex = 0;
var loginName;

var InputType = {           //Something like ENUM to distinguish between textfields
    username : 'username',     // TODO : create for all
    password : 'password',
    staticText1 : 'staticText1',
    staticText2 : 'staticText2',
    staticText3 : 'staticText3',
    staticText4 : 'staticText4',
    staticText5 : 'staticText5'
};

 var staticTextsValues = {
     staticText1 : "a",
     staticText2 : "b",
     staticText3 : "c",
     staticText4 : "d",
     staticText5 : "e"
 };

//prototype for storing keystroke
function Keystroke(keyCode, timeDown, timeUp) {
    this.keyCode = keyCode;
    this.timeDown = timeDown;
    this.timeUp = timeUp;
}

//serializer for keystroke data
function keystrokeSerialize() {
    return this.keyCode + "," + this.timeDown + "," + this.timeUp;
}


Keystroke.prototype.serialize = keystrokeSerialize;

//TODO : Create a function "resetAfterMistake" for resetting field when a typo or mistake occurs and to reset the keylogs data for that as well
function deleteKeyLogsEntry(index, inputType)
{
console.log("reset called..deleting : "+keyLogs[inputType][index-1].keyCode);
keyLogs[inputType].splice(index-1, 1);
resetsInAWord[inputType]++;
console.log("mistakes in the word : " + resetsInAWord[inputType]);
}


//function to bind the listener and to store the info in keylogs DS
function monitorTextField( textField, inputType, event ) {
    var field= '#'+textField;
    var value = document.getElementById(textField).value;
    var totalEntries =0;
    var i =0;
    if(value !== null){
    if(keyLogs[inputType].length > 0)
        totalEntries = keyLogs[inputType].length;
    }
    if(event.type == 'keydown')
    {
        var eventRecording = true;
        if( event.keyCode == 8 || event.keyCode == 46) { // backspace = 8, delete = 46
            var entryToDelete = doGetCaretPosition(textField);
            if(event.keyCode == 46)
            deleteKeyLogsEntry(entryToDelete + 1,inputType);
        else
            deleteKeyLogsEntry(entryToDelete,inputType);
            eventRecording = false;
        } else if( event.keyCode == 13 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39) { // Ignore enters and tabs
            eventRecording = false;
        } else if( totalEntries > 0 ) { // If the keylog isn't empty
            // if this isn't identical to the previous
            if( keyLogs[inputType][totalEntries - 1].timeDown === event.timeStamp
                && keyLogs[inputType][totalEntries - 1].keyCode === event.keyCode ) {
                eventRecording = false;
            console.log("not adding because of multiple press");
            }
        }

        if( eventRecording ) {
            keyLogs[inputType][totalEntries] = new Keystroke( event.keyCode, event.timeStamp, 0 );
        }
    }

    if(event.type == 'keyup')
    {    
        var i; // assume it's the last character
        for( i = totalEntries - 1; i >= 0; i-- ) {
            if( keyLogs[inputType][i].keyCode == event.keyCode && keyLogs[inputType][i].timeUp == 0 ) {
                keyLogs[inputType][i].timeUp = event.timeStamp;
                break;
            }
        }
    }
    if(event.type == 'keypress')
    {
        
    }
    if(value === staticTextsValues.staticText1)
    {
        document.getElementById('staticText2').style.display = 'block';
        document.getElementById('staticTextLabel2').style.display = 'block';
        document.getElementById('staticText1').disabled = 'true';
        document.getElementById('staticText2').focus();
    }
    if(value === staticTextsValues.staticText2)
    {
        document.getElementById('staticText3').style.display = 'block';
        document.getElementById('staticTextLabel3').style.display = 'block';
        document.getElementById('staticText2').disabled = 'true';
        document.getElementById('staticText3').focus();
    }
    if(value === staticTextsValues.staticText3)
    {
        document.getElementById('staticText4').style.display = 'block';
        document.getElementById('staticTextLabel4').style.display = 'block';
        document.getElementById('staticText3').disabled = 'true';
        document.getElementById('staticText4').focus();
    }
    if(value === staticTextsValues.staticText4)
    {
        document.getElementById('staticText5').style.display = 'block';
        document.getElementById('staticTextLabel5').style.display = 'block';
        document.getElementById('staticText4').disabled = 'true';
        document.getElementById('staticText5').focus();
    }
    if(value === staticTextsValues.staticText5)
    {
        document.getElementById('staticText5').disabled = 'true';
        
    }

}

function unMonitorTextField( textField ) {

    textField.unbind('keyup');
    textField.unbind('keydown');
}


$(document).ready(main);

function main(){

    //get session
    loginName = getCookie("loginName");
    if(loginName !== null)
    {
        document.getElementById("ks_username_display").value(loginName);

    }
    setCookie("reloadIndex","",-1);
    disableStaticTexts();

    for( var i in InputType) {
        keyLogs[i] = [];
        resetsInAWord[i] = 0;
    }

    $("input").bind("keypress keyup keydown",function(event){
    monitorTextField(this.id, this.name, event);
    });

    }


function serialisedEntiredata( inputType ) {
    var s = "";
    //var key = "#" + getTextField(inputType).attr('id');
    for (var i = 0; i < keyLogs[inputType].length; i++) {
        s += keyLogs[inputType][i].serialize() + " ";
    }
    console.log(s);
    return s;
}

//TODO : create an array to which you will push all the text fields for monitoring using that very top INPUTTYPE field

//TODO : Then iterate thru the array to bind listener to all fields using function created in TODO 2

//TODO : invoke a method created in todo 3 to handle form data submit, u can create again an array push all the form into it and the iterating over each invoke method mantaining modularity of methods



//create a main method where you will disable autocomplete for chrome browser
/*
** Returns the caret (cursor) position of the specified text field.
** Return value range is 0-oField.value.length.
*/
function doGetCaretPosition (textField) {


  var oField = document.getElementById(textField);
  // Initialize
  var iCaretPos = 0;

  // IE Support
  if (document.selection) {

    // Set focus on the element
    oField.focus();

    // To get cursor position, get empty selection range
    var oSel = document.selection.createRange();

    // Move selection start to 0 position
    oSel.moveStart('character', -oField.value.length);

    // The caret position is selection length
    iCaretPos = oSel.text.length;
  }

  // Firefox support
  else if (oField.selectionStart || oField.selectionStart == '0')
    iCaretPos = oField.selectionStart;

  // Return results
  console.log("caret position: "+ iCaretPos);
  return iCaretPos;
}

function disableStaticTexts()
{

console.log("in static texts");
document.getElementById('staticText2').style.display = 'none';
document.getElementById('staticTextLabel2').style.display = 'none';
document.getElementById('staticText3').style.display = 'none';
document.getElementById('staticTextLabel3').style.display = 'none';
document.getElementById('staticText4').style.display = 'none';
document.getElementById('staticTextLabel4').style.display = 'none';
document.getElementById('staticText5').style.display = 'none';
document.getElementById('staticTextLabel5').style.display = 'none';
document.getElementById('staticText1').focus();
}

function doAjaxPost() {
    // get the form values
        setKeystrokeValueToFields();
        var frm = $('#formInputRecord');
        var dataToSend ={
            username : document.getElementById('username').value,
            userTimeArray : document.getElementById('userTimeArray').value,
            pwdTimeArray : document.getElementById('pwdTimeArray').value,
            firstTimeArray : document.getElementById('firstTimeArray').value,
            secondTimeArray : document.getElementById('secondTimeArray').value,
            thirdTimeArray : document.getElementById('thirdTimeArray').value,
            fourthTimeArray : document.getElementById('fourthTimeArray').value,
            fifthTimeArray : document.getElementById('fifthTimeArray').value
        }
        console.log(JSON.stringify(dataToSend));
    $.ajax({
        contentType:'application/json; charset=UTF-8',
        dataType: 'json',
        type: frm.attr('method'),
        url: frm.attr('action'),
        data: JSON.stringify(dataToSend),
        success: function(response){
        //window.location.reload(true);
        window.location.reload(true);
         },
         error: function(e){
             alert('Error: ' + e);
             window.location.reload(true);
         }
    });

}

function doAjaxGet()
{
    $.ajax({
      type: "GET",
      url: "http://ec2-52-26-120-166.us-west-2.compute.amazonaws.com:8082/ksdynamics/trainData",
      cache: false,
      success: function(data){
         alert('success');
         window.open("http://ec2-52-26-120-166.us-west-2.compute.amazonaws.com:8080/index.html","_self");
      }
    });

}

function ConvertFormToJSON(form){
    var array = jQuery(form).serializeArray();
    var json = {};
    
    jQuery.each(array, function() {
        json[this.name] = this.value || '';
    });
    
    return json;
}


function endSession()
{
    reloadpage = 1;
    setCookie("loginName","",-1)
    setCookie("reloadIndex","",-1);
}

function setCookie(name,value,days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        var expires = "; expires="+date.toGMTString();
    }
    else var expires = "";
    document.cookie = name+"="+value+expires;
}

function getCookie(name) 
{
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function setKeystrokeValueToFields()
{
    document.getElementById('pwdTimeArray').value = serialisedEntiredata(InputType.password);
    document.getElementById('userTimeArray').value = serialisedEntiredata(InputType.username);
    document.getElementById('firstTimeArray').value = serialisedEntiredata(InputType.staticText1);
    document.getElementById('secondTimeArray').value = serialisedEntiredata(InputType.staticText2);
    document.getElementById('thirdTimeArray').value = serialisedEntiredata(InputType.staticText3);
    document.getElementById('fourthTimeArray').value = serialisedEntiredata(InputType.staticText4);
    document.getElementById('fifthTimeArray').value = serialisedEntiredata(InputType.staticText5);

}

function submitForm()
{
    reloadIndex = getCookie('reloadIndex');
    console.log("cookie value "+ reloadIndex);

    if(reloadIndex === null)
    {
        //first time call
        setCookie('reloadIndex','0',7);
        doAjaxPost();

    }
    else
    {
        var x = parseInt(reloadIndex, 10); // you want to use radix 10
    // so you get a decimal number even with a leading 0 and an old browser
        if(x < 10)
        {
            x++;
            setCookie('reloadIndex',x,7);
            doAjaxPost();
        }
        else
        {
            endSession();
            doAjaxGet();
            
        }

    }
}


var keyLogs = [];       //DS to store keystroke
var resetsInAWord = [];
var wrongAuthenticationRetries = 0;


var InputType = {           //Something like ENUM to distinguish between textfields
    username : 'username',     // TODO : create for all
    password : 'password',
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
console.log("reset called..deleting : " + keyLogs[inputType][index-1].keyCode);
keyLogs[inputType].splice(index-1, 1);
resetsInAWord[inputType]++;
console.log("mistakes in the word : " + resetsInAWord[inputType]);
}


//function to bind the listener and to store the info in keylogs DS
function monitorTextField( textField, inputType, event ) {
    var field= '#'+textField;
    var totalEntries =0;
    var i =0;
    if(keyLogs[inputType].length > 0)
    {
        totalEntries = keyLogs[inputType].length;
        console.log(keyLogs[inputType].length);
    }
    console.log(keyLogs[inputType]);
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
}

function unMonitorTextField( textField ) {

    textField.unbind('keyup');
    textField.unbind('keydown');
}


//TODO 2 :create a function to bind the listener to the respective fields, if any doubt contact me   


// TODO 3 : create a method to handle form data submit something like below :
// function formSubmitHandle( inputType ) {

//     var form = $( "#" + inputType );
//     var inputField = getField(inputType);

//     form.submit(function(event) {
//         var dataOkay = true;

//         if( inputType === InputType.username ) {
//             // If the password was wrong, raise an alert
//             if( inputField.val() !== inputField.attr('placeholder') ) {
//                 resetAfterMistake( inputField );
//                 dataOkay = false;
//                 return false;
//             }
//         } else if( inputType === InputType.password ) {
//             // Ensure the passwords match
//             var passwordField = $("#password");
//             var passwordRepeatField = $("#passwordRepeat");
//             if( passwordField.val() !== passwordRepeatField.val() ) {
//                 // Reset the passwords
//                 passwordField.val('');
//                 passwordRepeatField.val('');

//                 // Insert an alert
//                 console.log("Error: passwords don't match");
//                 if( $('.alert').length === 0 ) {
//                     passwordField.parent().parent().before(
//                         '<div class="alert alert-block alert-error fade in pull-right">'
//                             + '<button type="button" class="close" data-dismiss="alert" data-close="bindHelpPopup($(#' + inputField.attr('id') + '))">×</button>'
//                             + '<h4 class="alert-heading">Passwords do not match</h4>'
//                             + '<p>We\'ve reset the form so that you can retype your password.</p>'
//                             + '<p>'
//                             + '<a class="btn" href="#" onclick="$(\'.alert\').alert(\'close\');">OK</a>'
//                             + '</p>'
//                             + '</div>');
//                 }
//                 return false;
//             }
//         }// similarly handle for others, i have not added all.

//         // Add the invisible field which will allow us to send timing data
//         if( dataOkay ) {
//             // If this is an account creation or account training, set the keystroke data 
//             if( inputType === InputType.//CREATE || inputType === InputType.//TRAIN ) {
//                 getTimingField(InputType.//monitoredFIeld).val(serialisedEntiredata(monitoredField));
//             }

//             // Set the key phrase's timing data
//             getTimingField(inputType).val(serialisedEntiredata(inputType));
//             return true;
//         } else {

//             return false;
//         }
//     });
// }// this is just a prototype add if something is missing

// TODO create getTimingField method which will return the hidden field for storing data
function main(){
//disable atocomplete
 /*if( navigator.userAgent.toLowerCase().indexOf('chrome') >= 0 ) {
        setTimeout(function () {
            document.getElementById('userName').autocomplete = 'off';
        //TODO disable for all

        }, 1);*/
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
    for (var i = 0; i < keyLogs[inputType].length; i++) {
        s += keyLogs[inputType][i].serialize() + " ";
    }
    return s;
}

//TODO : create an array to which you will push all the text fields for monitoring using that very top INPUTTYPE field

//TODO : Then iterate thru the array to bind listener to all fields using function created in TODO 2

//TODO : invoke a method created in todo 3 to handle form data submit, u can create again an array push all the form into it and the iterating over each invoke method mantaining modularity of methods



//create a main method where you will disable autocomplete for chrome browser

$(document).ready(main)

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
  return iCaretPos;
}

function doAjaxPost() {
    // get the form values
        setKeystrokeValueToFields();
        var frm = $('#signInForm');
        var dataToSend ={
            username : document.getElementById('username').value,
            userTimeArray : document.getElementById('userTimeArray').value,
            password : document.getElementById('password').value,
            pwdTimeArray : document.getElementById('pwdTimeArray').value
        }
        console.log(JSON.stringify(dataToSend));
    $.ajax({
        contentType:'application/json; charset=UTF-8',
        dataType: 'json',
        type: frm.attr('method'),
        url: frm.attr('action'),
        data: JSON.stringify(dataToSend),
        success: function(response){
            setCookie("loginName",document.getElementById('username').value,7);
            // we have the response
        alert('success');
        window.open("http://ec2-52-26-120-166.us-west-2.compute.amazonaws.com:8080/staticTexts.html","_self");
        //window.open("index.html","_self")
         },
         error: function(e){
             alert('Error: ' + e);
             window.location.reload(true);
         }
    });
}
function setKeystrokeValueToFields()
{
    document.getElementById('userTimeArray').value = serialisedEntiredata(InputType.username);
    document.getElementById('pwdTimeArray').value = serialisedEntiredata(InputType.password);
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

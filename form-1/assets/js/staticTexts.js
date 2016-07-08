var keyLogs = [];       //DS to store keystroke
var resetsInAWord = [];
var wrongAuthenticationRetries = 0;
var reloadpage = 10;
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
     staticText3 : 'c',
     staticText4 : 'd',
     staticText5 : 'e'
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
    if(keyLogs[inputType].length > 0)
        totalEntries = keyLogs[inputType].length;
    //console.log(keyLogs[inputType]);
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
        submitForm();
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

    //get session
    loginName = getCookie("loginName");
    if(loginName === null)
    {
        alert('user logged out');
    }
    else
    {
        document.getElementById("ks_username_display").value(loginName);

    }
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
    var key = "#" + getTextField(inputType).attr('id');
    for (var i = 0; i < keyLogs[key].length; i++) {
        s += keyLogs[key][i].serialize() + " ";
    }
    return s;
}

//TODO : create an array to which you will push all the text fields for monitoring using that very top INPUTTYPE field

//TODO : Then iterate thru the array to bind listener to all fields using function created in TODO 2

//TODO : invoke a method created in todo 3 to handle form data submit, u can create again an array push all the form into it and the iterating over each invoke method mantaining modularity of methods



//create a main method where you will disable autocomplete for chrome browser

$(document).ready(main);

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

function submitForm()
{
   alert('success'); 
   reloadpage--;
   if(reloadpage == 0)
   {

   }
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


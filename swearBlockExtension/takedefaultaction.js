//TODO: look into storage for preference on wethere to call main() or not

var gettingItem = browser.storage.local.get("enabled");
gettingItem.then(onGot, onError);
/*
if(gettingItem!==undefined){
	
}
*/

function onGot(item) {
  console.log(item);
  
  if(item.enabled){
  	main();
  }
}

function onError(error) {
  console.log(`Error: ${error}`);
  
  //set the variable
  browser.storage.local.set({
	enabled: true 
  });
}

//look into storage for preference on wethere to call main() or not
function defaultaction(){
	var gettingItem = browser.storage.local.get("enabled");
	gettingItem.then(onGot, onError);
}

defaultaction();

function onGot(item) {
  console.log(item);
  
  if(item.enabled){
  
  	var gettingItem2 = browser.storage.local.get("sensitivity");
	gettingItem2.then(function(item){
	  	main(item.sensitivity);
	}, function(error){
		browser.storage.local.set({
			sensitivity: -2 
		  });
		  //try to repeat the action
		  defaultaction();
	});

  }
}

function onError(error) {
  console.log(`Error: ${error}`);

  browser.storage.local.set({
	enabled: true 
  });
  defaultaction();
}

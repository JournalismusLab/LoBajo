

function enable(){
	browser.storage.local.set({
		enabled: true 
	});

	var gettingItem4 = browser.storage.local.get("sensitivity");

	gettingItem4.then(function(item){
		document.location.reload();
	  	main(item.sensitivity);
	}, function(error){
		browser.storage.local.set({
			sensitivity: -2 
		});
		enable();
	});

}

enable();


//localStorage doesnt work here
//localStorage.setItem("popup","true");

//hide the button which is not needed based on the user settings

var gettingItem = browser.storage.local.get("enabled");
gettingItem.then(onGot, function(error){
	browser.storage.local.set({
		enabled: false
	});

});

var gettingItem2 = browser.storage.local.get("sensitivity");
gettingItem2.then(onGot2, function(error){
	browser.storage.local.set({
		sensitivity: -2
	});

});

function onGot2(item){
	document.getElementById("sensitivity").value=item.sensitivity;	
}

function onGot(item) {
  
  if(item.enabled){
	document.getElementById("button_disable").style.display="";
	document.getElementById("button_enable").style.display="none";
	document.getElementById("sensitivity_div").style.display="";
  }else{
	document.getElementById("button_disable").style.display="none";
	document.getElementById("button_enable").style.display="";
	document.getElementById("sensitivity_div").style.display="none";	
  }
}


document.getElementById("button_disable").addEventListener("click", function(e) {

  	function onExecuted(result) {
	  console.log(`We executed in all subframes`);
	  location.reload();
	}

	function onError(error) {
	  console.log(`Error: ${error}`);
	}

	var executing = browser.tabs.executeScript({
	  file: "/reset.js",
	  allFrames: true
	});
	executing.then(onExecuted, onError);
});

document.getElementById("button_enable").addEventListener("click", function(e) {

  	function onExecuted(result) {
	  console.log(`We executed in all subframes`);
  	  location.reload();
	}

	function onError(error) {
	  console.log(`Error: ${error}`);
	}

	var executing = browser.tabs.executeScript({
	  file: "/enable.js",
	  allFrames: true
	});
	executing.then(onExecuted, onError);
	
	
});


document.getElementById("sensitivity").addEventListener("change", function(e) {

	browser.storage.local.set({
		sensitivity: document.getElementById("sensitivity").value
	});
	
});



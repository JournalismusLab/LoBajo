
//localStorage doesnt work here
//localStorage.setItem("popup","true");

//TODO: hide the button which is not needed based on the user settings

document.getElementById("button_disable").addEventListener("click", function(e) {

  	function onExecuted(result) {
	  console.log(`We executed in all subframes`);
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

//console.log("popup");

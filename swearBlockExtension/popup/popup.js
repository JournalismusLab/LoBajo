

//localStorage.setItem("popup","true");

document.getElementById("button_reset").addEventListener("click", function(e) {


  var chosenPage = "https://www.google.com";;
  browser.tabs.create({
    url: chosenPage
  });

});

//console.log("popup");

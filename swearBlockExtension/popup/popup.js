//console.log("popup");

//localStorage.setItem("popup","true");

document.addEventListener("click", function(e) {


  var chosenPage = "https://www.google.com";;
  browser.tabs.create({
    url: chosenPage
  });

});

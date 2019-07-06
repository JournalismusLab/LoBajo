//document.body.style.border = "5px solid red";
console.log('swearblock is running');

var swear_words_en={};
var replacements=["😊","😀","😍","🤠"];

main();

//https://www.javascriptcookbook.com/article/traversing-dom-subtrees-with-a-recursive-walk-the-dom-function/

function walkTheDOM(node, func) {
    func(node);
    node = node.firstChild;
    while (node) {
        walkTheDOM(node, func);
        node = node.nextSibling;
    }
}

function workOnEachNode(node){
    if (node.nodeType === 3) { // Is it a Text node?
    	//console.log(node);
    	//node.data="*"+node.data+"*";
    	workOnNode(node);

    	/*
	var text = node.data.trim();
	if (text.length > 0) { // Does it have non white-space text content?
	    // process text
	}
	*/
    }
}

function main(){
	console.log("main");

	fill_swear_words();

	// Example usage: Process all Text nodes on the page
	walkTheDOM(document.body, workOnEachNode);
	
}


async function workOnNode(node){
	if(node.data.trim().length>0){
		node.data=makeNoHateSpeech(node.data);
	}
}


//console.log(hate_words);

function fill_swear_words(){
	console.log("filling swear words");

	var swear_words_en_arr = swear;
	
	swear_words_en_arr.forEach(function(str){
		swear_words_en[str.toLowerCase()]=true;
	});

	//DEBUG	
	swear_words_en["JavaScript".toLowerCase()]=true;

	//console.log(swear_words_en);
	//console.log(swear_words_en_arr);
	//console.log(swear_words_en);

}

function makeNoHateSpeechWord(word){
	
}

function makeNoHateSpeech(text){

	//console.log("inspect "+text);

	var result=text.split(" ");
	//console.log(result);
	
	result=result.map(function(word){
		//console.log("consider "+word);
		if(swear_words_en.hasOwnProperty(word.toLowerCase())){
			//it is swear word
			//console.log("replaced "+word);
			return replacements[Math.floor(Math.random()*replacements.length)];		
		}
		return word;	
	});
	
	return result.join(" ");
}

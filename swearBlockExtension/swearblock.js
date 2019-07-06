//document.body.style.border = "5px solid red";
console.log('swearblock is running');

var swear_words={};

//andrey said that sometimes older emojis are black and white in some fonts
//but the newer emojis have a higher chance of being colorful.
//so we use newer emojis, so that all emojis that we use  are displayed colorful
var replacements=["🙋🏼‍♂️","🐶","🦜","🌚","🤦‍♀️","🎼","🌅","🎅🏽"];

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
	
	var meta = document.createElement('meta');
	//meta.charset = "UTF-8";
	meta.setAttribute('charset', 'UTF-8');
	document.getElementsByTagName('head')[0].appendChild(meta);

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
	
	var time_1_ms = (new Date()).getTime();

	var swear_words_arr = swear.concat(swear_en);
	
	swear_words_arr.forEach(function(str){
		var lower = str.toLowerCase(); 
		swear_words[lower]=true;
		swear_words[lower+"s"]=true;
		swear_words[lower+"e"]=true;
		swear_words[lower+"en"]=true;
	});
	
	var time_2_ms = (new Date()).getTime();
	var duration = time_2_ms - time_1_ms;
	
	console.log("build swear words object "+duration+" milliseconds");

	//DEBUG	
	swear_words["JavaScript".toLowerCase()]=true;

}

function makeNoHateSpeechWord(word){
	
}

function strip_word_of_special_chars(word){
	return word
		.replace("!","")
		.replace("?","")
		.replace(",","")
		.replace(".","");
}

function makeNoHateSpeech(text){

	//console.log("inspect "+text);

	var result=text.split(" ");
	//console.log(result);
	
	result=result.map(function(word){
		//console.log("consider "+word);
		if(swear_words.hasOwnProperty(strip_word_of_special_chars(word.toLowerCase()))){
			//it is swear word
			//console.log("replaced "+word);
			return replacements[Math.floor(Math.random()*replacements.length)];		
		}
		return word;	
	});
	
	return result.join(" ");
}

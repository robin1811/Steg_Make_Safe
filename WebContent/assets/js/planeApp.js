const flightPath = {
	curviness: 1,
	autoRotate: true,
	values: [
		{ x: 150, y: -120 },
		{ x: 750, y: -50 },
		{ x: 1000, y: -150 },
		{ x: window.innerWidth, y: -120 }
	]
};

const tween = new TimelineLite();

tween.add(
	TweenLite.to(".paper-plane", 20, {
		bezier: flightPath,
		ease: Power1.easeInOut
	})
);

const controller = new ScrollMagic.Controller();
const scene = new ScrollMagic.Scene({
	triggerElement: '.animation',
	duration: 400,
	triggerHook: .5
})
	.setTween(tween)
	.setPin('.animation')
	.addTo(controller);

// Glowing stegnography heading
const text = document.querySelector(".fancy");
const strText = text.textContent;
const splitText = strText.split("");
text.textContent = "";
for (let i = 0; i < splitText.length; i++) {
	text.innerHTML += "<span>" + splitText[i] + "</span>";
}

let char = 0;
let timer = setInterval(onTick, 150);

function onTick() {
	const span = text.querySelectorAll("span")[char];
	span.classList.toggle("fade");
	char++;
	if (char == splitText.length) {
		char = 0;
		onTick();
	}
}

// show-image OR preview Image before upload
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function (e) {

			$('.show-image')
				.attr('src', e.target.result);
			$('.show-image').css("display", "block");
		};

		reader.readAsDataURL(input.files[0]);
	}
}

//loading
function setVisible(selector, visible) {
	document.querySelector(selector).style.display = visible ? 'block' : 'none';
}

let var1 = document.querySelector("#conv-btn");
if (var1 !== null) {
	document.querySelector("#conv-btn").addEventListener("click", function () {
		setVisible('#formatImage .loadingCube', true);
		$("#conv-btn").attr("value", "Converting");
	});
}

let var2 = document.querySelector("#encPreButton");
if (var2 !== null) {
	document.querySelector("#encPreButton").addEventListener("click", function () {
		setVisible('#predefineImage .loadingCube', true);
		$("#encPreButton").attr("value", "Encrypting");
		setTimeout(function () {
			setVisible(' .waitingMsg', true);
		}, 4000);
	});
}

let var3 = document.querySelector("#encButton");
if (var3 !== null) {
	document.querySelector("#encButton").addEventListener("click", function () {
		setVisible('#customImage .loadingCube', true);
		$("#encButton").attr("value", "Encrypting");
		setTimeout(function () {
			setVisible(' .waitingMsg', true);
		}, 4000);
	});
}

let var4 = document.querySelector("#decrBtn");
if (var4 !== null) {
	document.querySelector("#decrBtn").addEventListener("click", function () {
		setVisible('#decryptionSec .loadingCube', true);
		$("#decrBtn").attr("value", "Decrypting");
	});
}

$(".sidhu #inputMsg").keypress(function () {

	$("#predefineImage #inputMsg1").keyup(function () {

		var text = document.getElementById("inputMsg1").value;
		console.log(text.length);
		let m = 30 - text.length;
		console.log(m);
		document.getElementById("warningMsglimit1").innerHTML = "Remaining Letter Limit : <b>" + m + "</b>";
	})
	$("#predefineImage #inputMsg1").blur(function () {
		var text = document.getElementById("inputMsg1").value;
		console.log(text);
		if (text.length < 2) {
			setVisible('#predefineImage .alertMsg', true);
		}
		else {
			setVisible('#predefineImage .alertMsg', false);
		}
	})
	$("#customImage #inputMsg2").keyup(function () {
		console.log("custom image");
		var text = document.getElementById("inputMsg2").value;
		console.log(text.length);
		let m = 30 - text.length;
		console.log(m);
		document.getElementById("warningMsglimit2").innerHTML = "Remaining Letter Limit : <b>" + m + "</b>";
	})
	$("#customImage #inputMsg2").blur(function () {
		var text = document.getElementById("inputMsg2").value;
		console.log(text);
		if (text.length < 2) {
			setVisible('#customImage .alertMsg', true);
		}
		else {
			setVisible('#customImage .alertMsg', false);
		}
	})
});
$(".sidhu #inputPass").keypress(function () {
	$("#predefineImage #inputPass1").blur(function () {
		var text = document.getElementById("inputPass1").value;
		console.log(text);
		if (text.length < 5) {
			console.log("I am in");
			setVisible('#predefineImage .alertPass', true);
		}
		else {
			setVisible('#predefineImage .alertPass', false);
		}
	})
	$("#customImage #inputPass2").blur(function () {
		var text = document.getElementById("inputPass2").value;
		console.log(text);
		if (text.length < 5) {
			console.log("I am in");
			setVisible('#customImage .alertPass', true);
		}
		else {
			setVisible('#customImage .alertPass', false);
		}
	})
});

//Pop Up Msg
let popup = document.querySelector(".beforepopup");

function toggle() {
	document.querySelector(".download-btn-section").classList.toggle("active1");
	document.querySelector("#services").classList.toggle("active1");

	if (popup.style.display == "block")
		popup.style.display = "none";
	else
		popup.style.display = "block"
}

if (popup !== null) {
	popup.addEventListener("click", function () {
		document.querySelector("#services").classList.remove("active1");
		document.querySelector(".download-btn-section").classList.remove("active1");
		popup.style.display = "none";
	});
}


//QUOTES
var quotes = ["The good we secure for ourselves is precarious and uncertain until it is secured for all of us and incorporated into our common life.",
	"Tradition becomes our security, and when the mind is secure it is in decay",
	"Security is the chief enemy of mortals.",
	"Security is always excessive until it's not enough",
	"How long do you want these messages to remain secret I want them to remain secret for as long as men are capable of evil.",
	"Encryption provides enormous benefits to society by enabling secure communications, data storage, and online transactions.",
	"Without strong encryption, you will be spied on systematically by lots of people.",
	"I'm a strong believer in strong encryption", "Never tell everything you know…",
	"We live in a world that has walls and those walls need to be guarded by someone with gun",
	"On balance, the use of encryption, just like the use of good locks on doors, has the net effect of preventing a lot more crime than it might assist",
	"Properly implemented strong crypto systems are one of the few things that you can rely on"
]

var names = ["~ Jane Addams", "~ Jiddu Krishnamurti", "~ William Shakespeare", "~ Robbie Sinclair", "~ Neal Stephenson", "~ William Barr", "~ Whitfield Diffie", "~ Barack Obama", "~ Roger H. Lincoln", "~ Aaron Sorkin", "~ Matt Blaze", "~ Edward Snowden"]

function getQuotes() {
	let randomno = Math.floor(Math.random() * quotes.length);
	$("#quote h4").html(quotes[randomno]).fadeIn();
	$("#quote span").html(names[randomno]).fadeIn();

	setTimeout(function () {
		$("#quote h4").fadeOut();
		$("#quote span").fadeOut();
		setTimeout(function () {
			getQuotes();
		}, 200);
	}, 10000);
}
getQuotes();


//Drum KIT Game Waiting
$(".waitingMsg .drumBtn").click(function () {
	if ($(".beforepopupDrum").css("display") == "block") {
		$(".beforepopupDrum").css("display", "none");
		document.removeEventListener("keydown", drumSoundPlusAnim);
	}
	else {
		$(".beforepopupDrum").css("display", "block");
		document.addEventListener("keydown", drumSoundPlusAnim);
	}
});

function drumSoundPlusAnim(event) {
	makeSound(event.key);
	buttonAnimation(event.key);
}

//Drum JS
var numberOfDrumButtons = document.querySelectorAll(".drum").length;

for (var i = 0; i < numberOfDrumButtons; i++) {
	document.querySelectorAll(".drum")[i].addEventListener("click", function () {
		var buttonInnerHTML = this.innerHTML;
		makeSound(buttonInnerHTML);
		buttonAnimation(buttonInnerHTML);
	});
}

function makeSound(key) {
	switch (key) {
		case "w":
			var tom1 = new Audio("./assets/sounds/tom-1.mp3");
			tom1.play();
			break;
		case "a":
			var tom2 = new Audio("./assets/sounds/tom-2.mp3");
			tom2.play();
			break;
		case "s":
			var tom3 = new Audio('./assets/sounds/tom-3.mp3');
			tom3.play();
			break;
		case "d":
			var tom4 = new Audio('./assets/sounds/tom-4.mp3');
			tom4.play();
			break;
		case "j":
			var snare = new Audio('./assets/sounds/snare.mp3');
			snare.play();
			break;
		case "k":
			var crash = new Audio('./assets/sounds/crash.mp3');
			crash.play();
			break;
		case "l":
			var kick = new Audio('./assets/sounds/kick-bass.mp3');
			kick.play();
			break;
		default: console.log(key);
	}
}

function buttonAnimation(currentKey) {
	var activeButton = document.querySelector("." + currentKey);
	activeButton.classList.add("pressedDrum");
	setTimeout(function () {
		activeButton.classList.remove("pressedDrum");
	}, 100);
}

$(".closeIconDrum").click(function () {
	$(".beforepopupDrum").css("display", "none");
	document.removeEventListener("keydown", drumSoundPlusAnim);
});

// let darkChk =document.querySelector("#darkChk");
// let themeValue="";
// let valueeee;
// console.log(valueeee);
// if(valueeee==="true"){
// 	darkChk.click();
// }
// darkChk.addEventListener("change",function(){
	
// 	if(document.querySelector("#darkChk").checked){
// 		valueeee="true";
// 		themeValue="dark";
// 		console.log(themeValue);
// 	}
// 	else{
// 		valueeee="false";
// 		themeValue="light";
// 		console.log(themeValue);
// 	}
// })
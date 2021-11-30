const TxtRotate = function (el, toRotate, period) {
  this.toRotate = toRotate;
  this.el = el;
  this.loopNum = 0;
  this.period = parseInt(period, 10) || 2000;
  this.txt = '';
  this.tick();
  this.isDeleting = false;
};

let state = {}

TxtRotate.prototype.tick = function () {
  var i = this.loopNum % this.toRotate.length;
  var fullTxt = this.toRotate[i];
  if (fullTxt !== "fim.") {
    if (this.isDeleting) {
      this.txt = fullTxt.substring(0, this.txt.length - 1);
    } else {
      this.txt = fullTxt.substring(0, this.txt.length + 1);
    }

    this.el.innerHTML = '<span class="wrap">' + this.txt + '</span>';

    var that = this;
    var delta = 200 - Math.random() * 100;

    if (this.isDeleting) {
      delta /= 2;
    }

    if (!this.isDeleting && this.txt === fullTxt) {
      delta = this.period;
      this.isDeleting = true;
    } else if (this.isDeleting && this.txt === '') {
      this.isDeleting = false;
      this.loopNum++;
      delta = 100;
    }

    setTimeout(function () {
      that.tick();
    }, delta);
  } else {
    showCountDown({t: 0});
  }
};

function showCountDown(control) {
  $('#show-result').html(
      `
      <div class="dial-wrapper">
        <div class="dial-counter">10</div>
        <div class="dial-container dial-container1">
        <div class="dial-wedge"></div>
      </div>
      <div class="dial-container dial-container2">
        <div class="dial-wedge"></div>
      </div>
      <div class="dial-hand"></div>
      </div>
      `
  );

  var counter = document.querySelector('.dial-counter'),
      hand = document.querySelector('.dial-hand'),
      container1 = document.querySelector('.dial-container1 .dial-wedge'),
      container2 = document.querySelector('.dial-container2 .dial-wedge'),
      count = ~~counter.textContent;

  TweenLite.set(hand, { transformOrigin: '100% 100%' });
  TweenLite.set(container1, { transformOrigin: '0 50%' });
  TweenLite.set(container2, { transformOrigin: '100% 50%' });

  TweenLite.to(control, count, {
    t: count,
    onUpdate: function () {
      var t = control.t;
      TweenLite.set(hand, { rotation: t*360 });
      TweenLite.set(container1, { rotation: (t % 1 > 0.5) ? 180 : t*360 });
      TweenLite.set(container2, { rotation: (t % 1 < 0.5) ? 0 : t*360 + 180 });
      counter.textContent = Math.ceil(count - t);
    },
    ease:Linear.easeNone,
    onComplete: function () {
      showResult();
    }
  });
}

function renderTxt() {
  $("#show-result").html(
      `<div class="firstline">
          <span class="txt-rotate color" data-period="1200"
                data-rotate='[
                   "Então estão preparados?.",
                    
                    "fim."
                  ]'>

          </span>
          <span class="slash">|</span>
        </div>`
  )

  var elements = document.getElementsByClassName('txt-rotate');
  for (var i = 0; i < elements.length; i++) {
    var toRotate = elements[i].getAttribute('data-rotate');
    var period = elements[i].getAttribute('data-period');
    if (toRotate) {
      new TxtRotate(elements[i], JSON.parse(toRotate), period);
    }
  }
  // INJECT CSS
  var css = document.createElement("style");
  css.type = "text/css";
  css.innerHTML = ".txt-rotate > .wrap { border-right: 0em solid #666 ; }";
  document.body.appendChild(css);
}

function showResult() {
  $("#show-result").html(`
      <div class="about-main">
        <div class="about-first-paragraph wow">
           <span class="about-first-line"><span class="color">O destino é</span> ${state.placeName} </span>
             <br>
                 
            <span class="about-second-line">${state.description}</span>
        </div>

        <div class="about-img">
            <img src="${state.image}" alt="Image do resultado">
        </div>
      </div>
      
      <script>motion()</script>
   `)
}


const socket = new SockJS('/xenon');
stomp = Stomp.over(socket);

stomp.connect({}, function (frame) {
  stomp.subscribe('/topic/stations', function (message) {
    const body = $.parseJSON(message.body)
    if (body.placeName && body.description) {
      state = body;
      renderTxt();
    }
  })
})

function motion() {
  gsap.fromTo('.about-img', 1, {
    scale: 0,
    rotation: 180
  }, {
    scale: 1,
    rotation: 0
  })

  gsap.fromTo('.about-first-paragraph', 1, {
    x: -1000
  }, {
    x: 0
  })
}




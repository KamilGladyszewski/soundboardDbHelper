import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';


function getSeconds(time){
  var splt = time.split(":");
  var mins = splt[0];
  var secs = splt[1];

  var result = parseInt(mins) * 60 + parseInt(secs);

  return result;
}

class VideoList extends Component{
  constructor(){
    super();
    this.state = {
      filmy: null
    };
  }
  componentDidMount(){
    const URL = "http://localhost:8080/sounds/videos";
    fetch(URL)
      .then(res => res.json()
    ).then(json => {
      this.setState({ filmy: json });
    });
  }
  render(){
    const filmy = this.state.filmy;
    if (!filmy) return <div>Loading</div>;
    return(
      <div class="container">
         {filmy.map(film => <Video film = {film} /> )}

      </div>
    )
  }
}

class Video extends Component{
  render(){
    return(
      <div class = "row">
        <div class = "col-8">
          <img class = "video-img" src= {this.props.film.video.snippet.thumbnails.high.url} />
        </div>

        <SoundList  sounds = {this.props.film.sounds} />
      </div>
    )
  }
}

class SoundList extends Component{
  render(){
    return(
      <div class = "col-4 soundList">
      <ul>
        {this.props.sounds.map(sound => 
          <li>
            <a href = {"http://youtu.be/" + sound.vidId + "?t="+ getSeconds(sound.time)}>{sound.text}</a>
          </li>
        )}
        </ul>
      </div>  
    )
  }
}

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Soundboard Database Helper</h1>
        </header>
        <VideoList name="test" />
      </div>
    );
  }
}

export default App;

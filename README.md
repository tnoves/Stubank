# StuBank

StuBank is an Android app for the banking group StuBank. The app provides everyday banking for students, with a straightforward and easy interface.

## Installation

Download this repository and launch using Android Studio. Install the dependencies in the `build.gradle` file.

Download the StuBank API repository and and then download the API dependencies using pip:

```bash
pip install -r requirements.txt
```

Once all dependencies are installed, create a file in the StuBank API `app` folder called `login_details.txt` to include your
***Newcastle University login details*** so that a connection to the database can be made. The file must be written in the
format:

```bash
username password
```

## Usage

Run the `api_main.py` file, and the API will be launched at `127.0.0.1:5000`.

Then, launch the Android app.

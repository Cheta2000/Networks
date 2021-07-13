import http
from http.server import HTTPServer, BaseHTTPRequestHandler

HOST = 'localhost'
PORT = 8008


class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == "/":
            self.path = "//index.html"
        file = open("/home/mateusz/vsc-workspace/WWW/Lab1/"+self.path[1:]).read()
        self.send_response(200)
        self.end_headers()
        self.wfile.write(bytes(file, "utf-8"))
        #self.wfile.write(bytes(str(self.headers), "utf-8"))
        return http.server.SimpleHTTPRequestHandler.do_GET(self)

    # def do_POST(self):


print(f'Listening on http://{HOST}:{PORT}\n')

httpd = HTTPServer((HOST, PORT), SimpleHTTPRequestHandler)
httpd.serve_forever()

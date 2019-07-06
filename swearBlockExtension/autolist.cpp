#include <iostream>
#include <iomanip>
#include <string>
#include <fstream>
#include <list>
#include <iterator>

#ifdef __linux__
    #define CLRSCR() system("clear");
#elif _WIN32
    #include <windows.h>
    #define CLRSCR() cout << "\e[1;1H\e[2J";
#endif

using namespace std;

string trim(const string& str) {

    size_t first = str.find_first_not_of(' ');

    if(string::npos == first)
        return str;

    size_t last = str.find_last_not_of(' ');

    return str.substr(first, (last - first + 1));
}

bool file_exists(const string& file) {

   ifstream inFile(file);
   return inFile.good();
}

int main(void) {

   ifstream inFile; char x;

   list <string> swear; list <string> :: iterator it;
   string input = "";

   /****** Dialog ******/

   string inPath; string outPath;

   CLRSCR();
   cout << "Specify absolute Path to a List Document: ";
   cin >> inPath;

   if(file_exists(inPath) == false)
      return EXIT_FAILURE;

   cout << "\nSpecifiy absolute Path to a saving location: ";
   cin >> outPath;

   cout << endl;

   /****** Dialog ******/

   inFile.open("/home/milo/Schreibtisch/list");

   while(getline(inFile, input)) {

      input = trim(input);
      swear.push_front(input);
   }

   ofstream outFile;
   outFile.open(outPath);  // todo; file_exist check
   outFile << "var swear = [ ";

   int row = 0;

   for(it = swear.begin(); it != swear.end(); ++it) {

      string word = *it;

      outFile << "'" << word << "', ";
      row += 1;

      if(row % 10 == 0)
         outFile << "\n";
   }

   outFile << " ];";
   outFile.close();

   return EXIT_SUCCESS;
}

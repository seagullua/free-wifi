#include "MainWindow.h"
#include "ui_MainWindow.h"
//#include "../../free-wifi-common/freewifi/Test.h"
#include "freewifi/Test.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    runTests();
}

MainWindow::~MainWindow()
{
    delete ui;
}

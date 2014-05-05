#include "MainWindow.h"
#include "ui_MainWindow.h"
//#include "../../free-wifi-common/freewifi/Test.h"
#include "freewifi/Test.h"
MainWindow* MainWindow::_instance = nullptr;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    _instance = this;
    ui->setupUi(this);
    runTests();
}

MainWindow* MainWindow::getInstance()
{
    return _instance;
}

void MainWindow::writeToLog(const char* message)
{
    ui->log->append(message);
}

MainWindow::~MainWindow()
{
    _instance = nullptr;
    delete ui;
}

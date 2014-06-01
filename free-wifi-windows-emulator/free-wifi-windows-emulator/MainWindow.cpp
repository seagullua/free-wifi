#include "MainWindow.h"
#include "ui_MainWindow.h"

#include <freewifi/API.h>
#include "platform/PlatformImpl.h"
#include <freewifi/Test.h>

MainWindow* MainWindow::_instance = nullptr;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    _instance = this;
    ui->setupUi(this);

    API::getInstance()->init(API::PlatformPtr(new PlatformImpl));

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

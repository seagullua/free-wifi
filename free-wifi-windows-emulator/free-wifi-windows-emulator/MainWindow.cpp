#include "MainWindow.h"
#include "ui_MainWindow.h"
#include "freewifi/Test.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    Test a;
}

MainWindow::~MainWindow()
{
    delete ui;
}

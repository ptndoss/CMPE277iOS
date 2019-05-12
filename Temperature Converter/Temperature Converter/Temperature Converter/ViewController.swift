//
//  ViewController.swift
//  Temperature Convertor
//
//  Created by Alagar Nammalvar on 5/10/19.
//  Copyright © 2019 SJSU. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var value1: UITextField!
    // @IBOutlet weak var value1: UITextField!
    
    var value2 = ""
   
    @IBAction func convertf(_ sender: Any) {
        var a = (value1.text as! NSString).floatValue
        a = ((( a - 32) / 1.8) * 100).rounded() / 100
        self.value2 = "\(a) "
        self.view.backgroundColor = UIColor.green
       performSegue(withIdentifier: "segue1", sender: self)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        var vc = segue.destination as! SubViewController
        vc.outputval = self.value2
    }
    
}


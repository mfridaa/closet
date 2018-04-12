//
//  SearchBarDisplayController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 12..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit

class SearchController: UIViewController {

    @IBOutlet weak var searcBar: UISearchBar!
    
    @IBAction func swipeDownGesture(_ sender: Any) {
        print("asd")
        searcBar.isHidden = searcBar.isHidden == true ? false : true
    }
}

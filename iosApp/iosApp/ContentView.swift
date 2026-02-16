//
//  ContentView.swift
//  iosApp
//
//  Created by Sudhanshu Kumar  on 09/09/25.
//
import shared
import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text(Greetings().greet())
        }
        .padding()
        .onAppear {
            // Test shared module integration
            SharedLogger.shared.i(tag: "ContentView", message: "KMM integration working! iOS ContentView appeared successfully")
        }
    }
}

#Preview {
    ContentView()
}

